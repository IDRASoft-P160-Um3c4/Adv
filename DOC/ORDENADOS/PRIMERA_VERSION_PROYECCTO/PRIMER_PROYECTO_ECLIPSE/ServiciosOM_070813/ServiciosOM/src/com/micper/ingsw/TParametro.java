package com.micper.ingsw;

import java.util.*;

/**
 * Clase encargada de obtener los valores del archivo globales.properties.
 * <p>En esta clase se conservarán los parámetros de configuración de cada módulo en un solo archivo de propiedades.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * @author <dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-*.jsp\n-pgXXXXXXXCFG.java\n-Archivos de ingenieria de software com.micper.ingsw.*.java');">Click para mas información</a></small>
 * </dd>
 * <p></p><DT><strong>Diagrama de Clase:</strong>
 * <p><img src="TParametro.png">
 */

public class TParametro {
  /** Variable que contendrá todos los atributos del archivo de propiedades. */
  static private boolean lCreate = false;
  static TGetADMSEG tGetADMSEG = new TGetADMSEG();
  static private HashMap hmPropiedades = null;
  /** Variable que contendrá el identificador del objeto en el momento de instanciar. */
  int iIdent = 0;
  /** Variable que contiene el nombre del archivo de propiedades a emplear. */

  /**
   * Constructor que se encarga de cargar los parámetros de configuración del archivo globales.properties.
   * @param cId Identificador que se asignará al objeto que contiene los parámetros cargados.
   */
  public TParametro(String cId) {
    iIdent = new Integer(cId).intValue();
    if (lCreate == false) {
      lCreate = true;
      this.setParametros();
    }
  }

  private void setParametros() {
    try {
      TGetADMSEG tGetADMSEG = new TGetADMSEG();
      String cDataSource = tGetADMSEG.getPropEspecifica("ConDB");
      hmPropiedades = tGetADMSEG.getPropiedades(iIdent, cDataSource);
    }
    catch (Exception e) {
      System.out.print("TParametro.setParametros");
      e.printStackTrace();
    }
  }

  /**
   * Este método se encarga de cargar los atributos, dependiendo del valor del parámetro se sobreescriben o no.
   * @param forceReload Valor true indicará que se obligue a recargar los valores, false solo si no se ha cargado.
   */
  public void reload(boolean forceReload) {
    try {
      hmPropiedades = null;
      String cDataSource = tGetADMSEG.getPropEspecifica("ConDB");
      hmPropiedades = tGetADMSEG.getPropiedades(iIdent, cDataSource);
    }
    catch (Exception e) {
      System.out.print("TParametro.reload");
      e.printStackTrace();
    }
  }

  /**
   * Este método se encarga de obtener el valor asignado a un parámetro en el archivo de propiedades.
   * <br>En este caso se obtiene el valor del parámetro que no tenga un prefijo de módulo.
   * @param cProp Nombre del parámetro cuyo valor se desea obtener.
   * @return Valor del parámetro deseado.
   */
  public String getPropGeneral(String cProp) {
    String cValor = "";
    try {
      cValor = ""+hmPropiedades.get(cProp);
      if(cValor.compareTo("null") == 0){
        cValor = "";
        System.out.println("TParametro.La Propiedad General: " + cProp +
                           " No Fue Encontrada!");
      }
    }
    catch (Exception e) {
      System.out.println("TParametro.getPropGeneral");
      e.printStackTrace();
    }
    return cValor;
  }

  /**
   * Este método se encarga de obtener el valor asignado a un parámetro en el archivo de propiedades.
   * <br>En este caso se obtendrá el valor del parámetro que tenga el prefijo que se estableció en el identificador.
   * @param cProp Nombre del parámetro cuyo valor se desea obtener.
       * @return Valor del parámetro deseado (correspondiente al que tenga el prefijo).
   */
  public String getPropEspecifica(String cProp) {
    String cValor = "";
    try {
      cValor = ""+hmPropiedades.get(iIdent + "." + cProp);
      if(cValor.compareTo("null") == 0){
        cValor = "" + tGetADMSEG.getPropEspecifica(cProp);
        if(cValor.compareTo("null") == 0){
          System.out.println("TParametro.La Propiedad Específica: " + iIdent +
                             "." + cProp +
                             " No Fue Encontrada!");
        }
      }
    }
    catch (Exception e) {
      System.out.println("TParametro.getPropEspecifica: " + cProp);
      e.printStackTrace();
    }
    return cValor;
  }
}
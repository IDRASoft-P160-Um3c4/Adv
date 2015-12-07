package com.micper.ingsw;

import java.util.*;

/**
 * Clase encargada de obtener los valores del archivo globales.properties.
 * <p>En esta clase se conservar�n los par�metros de configuraci�n de cada m�dulo en un solo archivo de propiedades.
 * @version 1.0
 * <p>
 * @author Tecnolog�a Inred S.A. de C.V.
 * @author <dd>Jos� Luis L�pez Hern�ndez
 * @author <dd>LSC. Rafael Miranda Blumenkron
 * <p>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-*.jsp\n-pgXXXXXXXCFG.java\n-Archivos de ingenieria de software com.micper.ingsw.*.java');">Click para mas informaci�n</a></small>
 * </dd>
 * <p></p><DT><strong>Diagrama de Clase:</strong>
 * <p><img src="TParametro.png">
 */

public class TParametro {
  /** Variable que contendr� todos los atributos del archivo de propiedades. */
  static private boolean lCreate = false;
  static TGetADMSEG tGetADMSEG = new TGetADMSEG();
  static private HashMap hmPropiedades = null;
  /** Variable que contendr� el identificador del objeto en el momento de instanciar. */
  int iIdent = 0;
  /** Variable que contiene el nombre del archivo de propiedades a emplear. */

  /**
   * Constructor que se encarga de cargar los par�metros de configuraci�n del archivo globales.properties.
   * @param cId Identificador que se asignar� al objeto que contiene los par�metros cargados.
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
   * Este m�todo se encarga de cargar los atributos, dependiendo del valor del par�metro se sobreescriben o no.
   * @param forceReload Valor true indicar� que se obligue a recargar los valores, false solo si no se ha cargado.
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
   * Este m�todo se encarga de obtener el valor asignado a un par�metro en el archivo de propiedades.
   * <br>En este caso se obtiene el valor del par�metro que no tenga un prefijo de m�dulo.
   * @param cProp Nombre del par�metro cuyo valor se desea obtener.
   * @return Valor del par�metro deseado.
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
   * Este m�todo se encarga de obtener el valor asignado a un par�metro en el archivo de propiedades.
   * <br>En este caso se obtendr� el valor del par�metro que tenga el prefijo que se estableci� en el identificador.
   * @param cProp Nombre del par�metro cuyo valor se desea obtener.
       * @return Valor del par�metro deseado (correspondiente al que tenga el prefijo).
   */
  public String getPropEspecifica(String cProp) {
    String cValor = "";
    try {
      cValor = ""+hmPropiedades.get(iIdent + "." + cProp);
      if(cValor.compareTo("null") == 0){
        cValor = "" + tGetADMSEG.getPropEspecifica(cProp);
        if(cValor.compareTo("null") == 0){
          System.out.println("TParametro.La Propiedad Espec�fica: " + iIdent +
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
package com.micper.util;

import java.util.*;

/**
 * Clase para cargar archivo de propiedades solicitado.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * <p>Esta clase se encarga de cargar un archivo de propiedades solicitado,
 * empleando el Locale de México.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @see <small><a href="./../ingsw/TListaErr.html">com.micper.ingsw.TListaErr</a></small>
 * @see <small><a href="./../ingsw/TParametro.html">com.micper.ingsw.TParametro</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="TFileProperties.png">
 */
public class TFileProperties {
  /** Nombre del archivo de propiedades */
  private String cFileName;
  /** Objeto Locale generado en base a código de país y de lenguaje */
  private Locale cLocale;
  /** Objeto que contiene el juego de llaves y valores */
  private ResourceBundle cResourceBundle;

  /**
   * Este constructor llama la creación de una nueva instancia del archivo
   * de propiedades en Español Mexicano.
   * @param pFileName Nombre del archivo que se desea obtener
   * <p>
   * @see <a href="JavaScript:alert('Documentación de Códigos de País e Idioma\n-java.util.Locale');">Click para mas información</a>
   */
  public TFileProperties(String pFileName) {
    this(pFileName, new Locale("es","MX"));

  }

  /**
   * Este constructor llama la creación de una nueva instancia del archivo
   * de propiedades en el Código de País y Código de Lenguaje especificados.
   * @param pFileName     Nombre del archivo que se desea obtener
   * @param pPaisCode     Nombre del código de país a usar
   * @param pLanguageCode Nombre del código de lenguaje a usar
   * <p>
   * @see <a href="JavaScript:alert('Documentación de Códigos de País e Idioma\n-java.util.Locale');">Click para mas información</a>
   */
  public TFileProperties(String pFileName, String pPaisCode, String pLanguageCode) {
    this(pFileName, new Locale(pLanguageCode, pPaisCode));
  }

  /**
   * Este constructor crea una nueva instancia del archivo de propiedades con
   * el Código de País y Código de Lenguaje especificados como Locale.
   * @param pFileName Nombre del archivo que se desea obtener
   * @param pLocale   Objeto Locale que se creó previamente, mismo con el que
   * se cargará el archivo
   * <p>
   * @see <a href="JavaScript:alert('Documentación de Códigos de País e Idioma\n-java.util.Locale');">Click para mas información</a>
   */
  public TFileProperties(String pFileName, Locale pLocale) {
    cFileName=pFileName;
    cLocale=pLocale;
    reload(false);
  }

  /**
   * Este método se encarga de realizar la carga de un archivo.
   * @param forceReload Si el valor es true, recarga el archivo cada vez que
   * se solicita, de lo contrario solo si el objeto es nulo
   * <p>
   * @see <a href="JavaScript:alert('Documentación sobre Recursos:\n-java.util.ResourceBundle');">Click para mas información</a>
   */
  public synchronized void reload(boolean forceReload) {
    if (cResourceBundle == null || forceReload) {
      cResourceBundle = ResourceBundle.getBundle(cFileName, cLocale);
      notify();
    }
  }

  /**
   * Este método se encarga de recuperar el valor de una llave del recurso indicado.
   * @param pKey Nombre de la llave cuyo valor desea recuperar
   * <p>
   * @return Valor de la propiedad solicitada
   * <p>
   * @see <a href="JavaScript:alert('Documentación sobre Recursos:\n-java.util.ResourceBundle');">Click para mas información</a>
   */
  public String getProperty(String pKey) {
    return cResourceBundle.getString(pKey);
  }
}

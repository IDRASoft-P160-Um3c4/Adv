package com.micper.sql;

import java.util.*;
import java.io.Serializable;

/**
 * Esta interfaz se encarga de envolver un Bean.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * <p>La finalidad de envolver a un bean es que la clase bean pueda manipularse
 * como un objeto HashBeanInterface, utilizando los métodos heredados de éste.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @see <small><a href="BeanScroller.html">BeanScroller</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Interfaz:</strong>
 * <p><img src="HashBeanInterface.png">
 */

public interface HashBeanInterface extends Serializable{

  /**
   * Método encargado de regresar los campos que forman la llave primaria del Bean.
   * @return Objeto BeanPK  que contiene los campos de la llave primaria del bean.
   */
  public BeanPK getPK();

  /**
   * Método encargado de regresar un hashmap con los nombres de los campos como llave en el hashmap y sus valores.
   * <br>Ejemplo: key="Tabla.campo", value=<valor delcampo>.
   * <br>Ejemplo: String valor = (String) miHashMap.get("Tabla.campo");
   * @return Objeto HashMap que contiene el valor de un campo proporcionado como llave (key).
   */
  public HashMap toHashMap();

}

package com.micper.sql;

import java.util.*;
import java.io.*;

/**
 * Implementa un scroll para recorrer el vector de beans.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * <p>Los objetos contenidos deben implementar la clas HashBEanInterface para poder ser manipulados por esta clase.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @see <small><a href="PageBeanScroller.html">PageBeanScroller</a></small>
 * @see <small><a href="./../ingsample/pg9900012CFG.html">com.micper.ingsample.pg9900012CFG</a></small>
 * @see <small><a href="./../ingsample/pg9902011CFG.html">com.micper.ingsample.pg9902011CFG</a></small>
 * @see <small><a href="./../ingsample/pg9903011CFG.html">com.micper.ingsample.pg9903011CFG</a></small>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-pg9900012.jsp\n-pg9902011.jsp\n-pg9903011.jsp');">Click para mas información</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="BeanScroller.png">
 */

public class BeanScroller implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = -2550809985795140838L;
/** Vector de Beans sobre el cual se navegará. */
  private Vector beans;
  /** Identificador de registro actual dentro del vector de beans. */
  private int rowIdx = -1;
  /** Bean actual dentro de la interfaz de beans. */
  private HashBeanInterface currentBean;

  /**
   * Crea un BeanScroller para el Vector de Beans proporcionado.
   * @param pBeans Es el vector de beans que se desea asignar al objeto.
   */
  public BeanScroller(Vector pBeans) {
    beans = pBeans;
  }

  /**
   * Método encargado de regresas el número de registros existentes.
   * @return int Número de registros existentes.
   */
  public int rowSize() {
    return beans.size();
  }

  /**
   * Método encargado de regresas el índice en el que está posicionado el cursor dentro del vector.
   * @return Número del registro actual.
   */
  public int rowNo() {
    return rowIdx + 1;
  }

  /**
   * Método que nos permite conocer si el registro actual tiene al menos un registro posterior.
   * @return Regresa true si tiene al menos un registro posterior, false en caso contrario.
   */
  public boolean hasNext() {
    if (rowSize() <= 1)
      return false;
    if (rowIdx > 0 && rowIdx >= (beans.size() - 1))
      return false;
    return true;
  }

  /**
   * Método que nos permite conocer si el registro actual tiene al menos un registro anterior.
   * @return Regresa true si tiene al menos un registro anterior, false en caso contrario.
   */
  public boolean hasPrev() {
    if (rowSize() <= 1)
      return false;
    if (rowIdx <= 0)
      return false;
    return true;
  }

  /**
   * Método encargado de posicionar el apuntador en el registro con el índice indicado.
   * @param pRowIdx Índice en el que se desea posicionar el registro.
   * @throws Exception La excepción que puede generarse es al tratar de obtener un registro inexistente.
   */
  public void setRowIdx(int pRowIdx) throws Exception {
    if (rowSize() <= 0)
      return;
    if (pRowIdx > 0 && pRowIdx <= rowSize()) {
      rowIdx = pRowIdx - 1;
    } else {
      throw new Exception("Registro fuera de límites: " + pRowIdx);
    }
    setCurrentBean();
  }

  /**
   * Método encargado de posiciona el apuntador del scroller en el registro con la PK proporcionada.
   * @param pBeanPK Objeto con los campos que forman la llave primaria del Bean.
   */
  public void setRowByPK(BeanPK pBeanPK) {
    for (int i = 0, len = rowSize(); i < len; i++) {
      HashBeanInterface mBeanInt = (HashBeanInterface)beans.elementAt(i);
      if (mBeanInt.getPK().equals(pBeanPK)) {
        try {
          setRowIdx(i + 1);
        } catch (Exception ex){
        }
        break;
      }
    }
  }

  /**
   * Método encargado de mover el cursor al primer registro del vector.
   */
  public void firstRow() {
    if (rowSize() > 0)
      rowIdx = 0;
    setCurrentBean();
  }

  /**
   * Método encargado de posicionar el cursor en el siguiente registro.
   * @return Valor true si se posicionó con exito, false en caso contrario.
   */
  public boolean nextRow() {
    if (rowIdx == rowSize()) {
      firstRow();
    } else if (rowSize() > 0) {
      rowIdx++;
    }
    if (rowSize() == 0 || rowIdx >= rowSize())
      return false;
    setCurrentBean();
    return true;
  }

  /**
   * Método encargado de posicionar el cursor en el registro siguiente al índice proporcionado.
   * @param pRowIdx Índice base para posicionarse en el registro siguiente.
   * @return Valor true si se posicionó con éxito, false en caso contrario.
   */
  public boolean nextTo(int pRowIdx) {
    try {
      setRowIdx(pRowIdx);
    } catch (Exception ex) {
    }
    return nextRow();
  }

  /**
   * Método encargado de posicionar el cursor en el registro anterior.
   * @return Valor true si se posicionó con éxito, false en caso contrario.
   */
  public boolean prevRow() {
    if (rowIdx == -1) {
      firstRow();
    } else if (rowSize() > 0) {
      rowIdx--;
    }
    if (rowSize() == 0 || rowIdx < 0)
      return false;
    setCurrentBean();
    return true;
  }

  /**
   * Método encargado de posicionar el cursor en el registro anterior al indice proporcionado.
   * @param pRowIdx Índice base para posicionarse en registro previo.
   * @return Valor true si se posicionó con éxito, false en caso contrario.
   */
  public boolean prevTo(int pRowIdx) {
    try { setRowIdx(pRowIdx); } catch (Exception ex) {}
    return prevRow();
  }

  /**
   * Método encargado de posicionar el cursor en el último registro.
   */
  public void lastRow() {
    if (rowSize()>0) rowIdx=rowSize()-1;
    setCurrentBean();
  }

  /**
   * Método encargado de establecer el objeto bean referente al índice actual.
   */
  protected void setCurrentBean() {
    if (rowSize() > 0)
      currentBean = (HashBeanInterface)beans.elementAt(rowIdx);
  }

  /**
   * Método encargado de regresear el bean referente al índice actual como Object, al que se le
   * puede realizar un CAST a la interface HashBeanInterface o a la clase que la implementa.
   * @return El objeto o interface HashBeanInterface implementado por el bean o null si no hay registros.
   */
  public Object getCurrentBean() {
    if (rowSize() <= 0)
      return null;
    return currentBean;
  }

  /**
   * Método encargado de regresar los nombres de los campos del bean en un objeto Iterator.
   * @return Objeto Iterator con los nombre de los campos o Iterator vacío si no hay registros.
   * @see java.util.Iterator
   */
  public Iterator getBeanFieldNames() {
    HashMap mBeanFields = (rowSize() > 0) ? currentBean.toHashMap():new HashMap();
    return mBeanFields.keySet().iterator();
  }

  /**
   * Método encargado de regresar el valor del campo proporcionado, null en caso de que no tenga valor asignado.
   * @param aFieldName Nombre del campo en formato "Tabla.campo" del que se desea obtener el valor.
   * @return El valor para ese campo en el registro actual o null si no hay registros.
   */
  public Object getFieldValue(String aFieldName) {
    if (rowSize() <= 0)
      return null;
    HashMap mBeanFields=currentBean.toHashMap();
    return mBeanFields.get(aFieldName);
  }

  /**
   * Método encargado de regresar el valor del campo proporcionado, o el parametro aDefault en caso de que el campo no tenga valor asignado.
   * @param aFieldName Nombre del campo en formato "Tabla.campo" del que se desea obtener el valor.
   * @param aDefault El valor a devolver por omisión si el campo no tiene valor.
   * @return El valor para ese campo en el registro actual ó el parámetro aDefault si es null.
   */
  public Object getFieldValue(String aFieldName, String aDefault) {
    Object lObj = getFieldValue(aFieldName);
    if (lObj == null)
      return aDefault;
    if (lObj.toString().compareToIgnoreCase("") == 0)
      return aDefault;
    return lObj;
  }

  /**
   * Método encargado de indicar si un campo existe y tiene valor.
   * @param pFieldName Nombre del campo en formato "Tabla.campo" del que se desea obtener el valor.
   * @return Valor true si el campo existe y tiene valor, false en caso contrario.
   */
  public boolean hasValue(String pFieldName) {
    Object lValue = getFieldValue(pFieldName, "");
    if (lValue.equals(""))
      return false;
    return true;
  }

  /**
   * Método encargado de regresar el vector de objetos con el cual se generó el BeanScroller.
   * @return Vector de Objetos.
   */
  public Vector getVectorBeans() {
    return beans;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
}

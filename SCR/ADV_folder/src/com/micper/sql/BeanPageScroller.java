package com.micper.sql;

import java.util.*;
import java.io.*;

/**
 * Esta clase se encarga de realizar la navegación en un conjunto de registros.
 * <p>Ingeniería de Software generada en JAVA (J2EE).
 * <p>La navegación se realiza a nivel página, es decir un grupo de registros.
 * <br>También permite conocer si exiten páginas antes o después de la actual, de tal
 * modo que pueda controlarse el panel de navegación y el reposicionamiento.
 * @version 1.0
 * <p>
 * @author Tecnología Inred S.A. de C.V.
 * @author <dd>José Luis López Hernández
 * <p>
 * @deprecated Use PageBeanScroller.
 * <p>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="BeanPageScroller.png">
 */

public class BeanPageScroller implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = -2738874478995514582L;
/** Número de paginas existentes en base al número de elementos resultantes. */
  private int pageSize = 0;
  /** Número de elementos que tendrá cada página. */
  private int linesXPage = 10;
  /** Elementos que se desean paginar. */
  private Vector beans;
  /** Indicará el número de página en la que se desea reposicionar. */
  private int pageIdx = -1;
  /** Indicará el número de renglón en el que se desea reposicionar. */
  private int rowIdx = -1;
  /** Indica cual es el primer número de renglón. */
  private int rowIdxMin = 0;
  /** Indica cual es el último número de renglón. */
  private int rowIdxMax = 0;

  /**
   * Constructor encargado de crear el objeto y asignar los valores proporcionados, asi como
   * también se encargará de inicializar algunas variables internas.
   * @param pBeans Vector de elementos que se desean paginar.
   * @param pLinesXPage Número de elementos que se desea tener por cada página.
   */
  public BeanPageScroller(Vector pBeans, int pLinesXPage) {
    if(pLinesXPage > 0)
      linesXPage = pLinesXPage;
    beans = pBeans;
    int lSize = beans.size() / linesXPage;
    int lRemain = beans.size() % linesXPage;
    if (lRemain > 0)
      pageSize = lSize + 1;
    else
      pageSize = lSize;
  }

  /**
   * Método encargado de devolver el número de páginas disponibles en el vector de elementos.
   * @return Número de páginas generadas.
   */
  public int size() {
    return pageSize;
  }

  /**
   * Método encargado de asignar el número de página que se desea desplegar (si existe).
   * @param pPageIdx Número de página que se desea colocar.
   * @throws Exception Excepción que puede arrojar es por intentar colocar una página no existente.
   */
  public void setPageIdx(int pPageIdx) throws Exception {
    if (pageIdx > 0 && pPageIdx <= pageSize) {
      pageIdx = pPageIdx - 1;
      rowIdx = -1;
      rowIdxMax = (pageIdx * linesXPage) + linesXPage;
      rowIdxMin = rowIdxMax - linesXPage;
    } else {
      throw new Exception("Página fuera de límites: " + pPageIdx);
    }
  }

  /**
   * Este método se encarga de devolver el número de página en la que nos encontramos ubicados.
   * @return Número de página en la que nos encontramos.
   */
  public int pageNo() {
    return pageIdx + 1;
  }

  /**
   * Método encargado de obtener la siguiente página, asignando los valores de renglón mínimo y máximo.
   */
  public void nextPage() {
    if (pageIdx >= size()) {
      pageIdx = 0;
    } else {
      pageIdx++;
    }
    rowIdx = -1;
    rowIdxMax = (pageIdx * linesXPage) + linesXPage;
    rowIdxMin = rowIdxMax - linesXPage;
  }

  /**
   * Método encargado de obtener la página anterior, asignando los valores de renglón mínimo y máximo.
   */
  public void previousPage() {
    if (pageIdx == 0) {
      pageIdx = size();
    } else {
      pageIdx--;
    }
    rowIdx = -1;
    rowIdxMax = (pageIdx * linesXPage) + linesXPage;
    rowIdxMin = rowIdxMax - linesXPage;
  }

  /**
   * Método encargado de reubicar la paginación en base al número de elemento proporcionado.
   * @param pRowIdx Identificador o número de renglón a ubicar.
   * @throws Exception La excepción que se arroja se debe a que se intenta ubicar un renglón inexistente.
   */
  public void setRowIdx(int pRowIdx) throws Exception {
    if (pRowIdx>rowIdxMin && pRowIdx <= rowIdxMax) {
      rowIdx = pRowIdx - 1;
    } else {
      throw new Exception("Registro fuera de límites: " + pRowIdx);
    }
  }

  /**
   * Método encargado de devolver el número de renglon en el que estamos ubicados.
   * @return Número de renglón seleccionado.
   */
  public int rowNo() {
    return rowIdx + 1;
  }

  /**
   * Método que se encarga de obtener el primer elemento de la página actual.
   */
  public void firstRow() {
    rowIdx = pageIdx * linesXPage;
  }

  /**
   * Método que se encarga de indicarnos si existe un renglón posterior al último desplegado.
   * @return Valor true si existe un registro adicional.
   */
  public boolean nextRow() {
    if (rowIdx == -1)
      firstRow();
    else
      rowIdx++;
    if (rowIdx >= rowIdxMax)
      return false;
    return true;
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
}

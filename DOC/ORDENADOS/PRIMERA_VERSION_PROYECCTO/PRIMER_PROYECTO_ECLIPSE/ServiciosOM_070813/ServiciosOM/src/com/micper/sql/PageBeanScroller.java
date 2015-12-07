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
 * @see <small><a href="./../ingsample/pg9900009CFG.html">com.micper.ingsample.pg9900009CFG</a></small>
 * @see <small><a href="./../ingsample/pg9902010CFG.html">com.micper.ingsample.pg9902010CFG</a></small>
 * @see <small><a href="./../ingsample/pg9903010CFG.html">com.micper.ingsample.pg9903010CFG</a></small>
 * @see <small><a href="JavaScript:alert('Consulte los archivos:\n-pg9900009.jsp\n-pg9902010.jsp\n-pg9903010.jsp');">Click para lista de JSP's</a></small>
 * </dd>
 * <p></p><dt><strong>Diagrama de Clase:</strong>
 * <p><img src="PageBeanScroller.png">
 */

public class PageBeanScroller extends BeanScroller implements Serializable {
  /**
	 *
	 */
	private static final long serialVersionUID = 3771089147271128393L;
/** Número de registros por cada página. */
  private int rowsXPage = 10;
  /** Número de páginas disponibles. */
  private int pageSize = 0;
  /** Identificador de página actual. */
  private int pageIdx = -1;
  /** Identificador de registro inicial dentro de la página. */
  private int rowIdxMin = 0;
  /** Identificador de registro final dentro de la página. */
  private int rowIdxMax = 0;

  /**
   * Constructor que se encarga de inicializar el vector de beans para hacer el cálculo de paginación.
   * @param pBeans Vector de beans que se desea paginar.
   * @param pRowsXPage Número de registros que se desean por página.
   */
  public PageBeanScroller(Vector pBeans, int pRowsXPage) {
    super(pBeans);
    setRowsXPage(pRowsXPage);
  }

  /**
   * Método encargado de asignar el número de registros que se desea por página, cambiando valores de variables de control.
   * @param pRowsXPage Número de registros que se asignará a cada página.
   */
  public void setRowsXPage(int pRowsXPage) {
    rowsXPage = pRowsXPage;
    if(pRowsXPage > 0)
      rowsXPage = pRowsXPage;
    int lSize = rowSize() / rowsXPage;
    if (rowSize() % rowsXPage > 0)
      pageSize = lSize + 1;
    else
      pageSize = lSize;
  }

  /**
   * Método encargado de devolver el número de páginas existentes en el vector.
   * @return Número de páginas existentes.
   */
  public int pageSize() {
    return pageSize;
  }

  /**
   * Método encargado de indicar el número de página actual.
   * @return Número de página actual.
   */
  public int pageNo() {
    return pageIdx + 1;
  }

  /**
   * Método encargado de indicar el número de registro inicial de la página.
   * @return Número de registro inicial de la página.
   */
  public int pageMinRow() {
    return rowIdxMin + 1;
  }

  /**
   * Método encargado de indicar el número de registro máximo de la página.
   * @return Número de registro máximo de la página.
   */
  public int pageMaxRow() {
    return rowIdxMax + 1;
  }

  /**
   * Método encargado de colocar el número de registro incial de la página.
   */
  private void setFirstRowOfPage() {
    rowIdxMin = pageIdx * rowsXPage;
    rowIdxMax = rowIdxMin + rowsXPage - 1;
    try {
      setRowIdx(rowIdxMin + 1);
    } catch (Exception ex) {
    }
  }

  /**
   * Método encargado de colocar el número de página indicado.
   * @param pPageIdx Número de página que se desea seleccionar.
   * @throws Exception El error que puede arrojarse se debe a que se selecciona una página inexistente.
   */
  public void setPageIdx(int pPageIdx) throws Exception {
    if (pageSize() <= 0)
      return;
    if (pPageIdx > 0 && pPageIdx <= pageSize())
      pageIdx = pPageIdx - 1;
    else
      throw new Exception("Página fuera de límites: " + pPageIdx);
    setFirstRowOfPage();
  }

  /**
   * Método encargado de indicar si existe una página siguiente o no.
   * @return Valor true en caso de existir una siguiente página, false en caso contrario.
   */
  public boolean hasNextPage() {
    if (pageSize() <= 1)
      return false;
    if (pageIdx > 0 && pageIdx >= (pageSize() - 1))
      return false;
    return true;
  }

  /**
   * Método encargado de indicar si existe una página previa o no.
   * @return Valor true en caso de existir una página previa, false en caso contrario.
   */
  public boolean hasPrevPage() {
    if (pageSize() <= 1)
      return false;
    if (pageIdx <= 0)
      return false;
    return true;
  }

  /**
   * Método encargado de seleccionar la primer página del vector.
   */
  public void firstPage() {
    if (pageSize() > 0)
      pageIdx = 0;
    setFirstRowOfPage();
  }

  /**
   * Método encargado de seleccionar la siguiente página del vector.
   * @return Valor true en caso de que sea exitoso el reposicionamiento, false en caso contrario.
   */
  public boolean nextPage() {
    if (pageIdx == pageSize())
      firstPage();
    else if (pageSize() > 0)
      pageIdx++;
    if (pageSize() == 0 || pageIdx >= pageSize())
      return false;
    setFirstRowOfPage();
    return true;
  }

  /**
   * Método encargado de seleccionar la siguiente página a la página proporcionada.
   * @param pPageIdx Número de página a la que se desea reposicionar.
   * @return Valor true en caso de que se pueda colocar la página siguiente, false en caso contrario.
   */
  public boolean nextPageTo(int pPageIdx) {
    try {
      setPageIdx(pPageIdx);
    } catch (Exception ex) {
    }
    return nextPage();
  }

  /**
   * Método encargado de seleccionar la página anterior del vector.
   * @return Valor true en caso de que sea exitoso el reposicionamiento, false en caso contrario.
   */
  public boolean prevPage() {
    if (pageIdx == -1)
      firstPage();
    else if (pageSize() > 0)
      pageIdx--;
    if (pageSize() == 0 || pageIdx < 0)
      return false;
    setFirstRowOfPage();
    return true;
  }

  /**
   * Método encargado de seleccionar la página previa a la página proporcionada.
   * @param pPageIdx Número de página a la que se desea reposicionar.
   * @return Valor true en caso de que se pueda colocar la página anterior, false en caso contrario.
   */
  public boolean prevPageTo(int pPageIdx) {
    try {
      setPageIdx(pPageIdx);
    } catch (Exception ex) {
    }
    return prevPage();
  }

  /**
   * Método encargado de seleccionar la última página del vector.
   */
  public void lastPage() {
    if (pageSize() > 0)
      pageIdx = pageSize() - 1;
    setFirstRowOfPage();
  }

  /**
   * Método encargado de posicionar el apuntador del scroller en el registro con la llave primaria proporcionada.
   * <br>Adicionalmente se posicionará en la página que se encuentra el registro.
   * @param pBeanPK Objeto BeanPK con los campos que forman la llave primaria del bean.
   */
  public void setPageByRowPK(BeanPK pBeanPK) {
    super.setRowByPK(pBeanPK);
    int mRowNo=rowNo();
    while(nextPage()) {
      if (mRowNo>=pageMinRow() && mRowNo<=pageMaxRow()) {
        break;
      }
    }
  }

  /**
   * Método que se encarga de inicializar los apuntadores del scroller.
   */
  public void start() {
    super.prevRow();
  }

  /**
   * Muve el cursor al siguiente registro en la página actual
   */
  /**
   * Método que se encarga de mover el cursor al siguiente registro en la página actual.
   * @return Valor true en caso que se haya logrado el reposicionamiento, false en caso contrario.
   */
  public boolean nextRow() {
    if (rowNo() < pageMaxRow() && rowNo() < rowSize()) {
      super.nextRow();
      return true;
    }
    return false;
  }

  /**
   * Método encargado de regresar un vector con los beans de la pagina actual.
   * @return Vector de beans de la página actual.
   */
public Vector getPageVector(){
    Vector vResultado = new Vector();
    Vector vBeans = super.getVectorBeans();
    int iMax = rowIdxMax;
    if (iMax > vBeans.size())
      iMax = vBeans.size();
    for (int i = rowIdxMin; i < iMax; i++)
      vResultado.add(vBeans.get(i));
    return vResultado;
  }
public Vector getPageKeys(){
    Vector vBeans = super.getVectorBeans();
    Vector vResultado = new Vector();
    if(vBeans.size()>0){
      vResultado.add(vBeans.get(0));
    }
    return vResultado;
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
}

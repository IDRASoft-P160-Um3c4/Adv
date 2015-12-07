package com.micper.util;

import java.io.*;
import com.micper.sql.*;

/**
 * Clase que genera un archivo de JavaScript (*.js) en el file system local
 * <p>
 * @version 1.0
 * <p>
 * @author Micros Personales S.A. de C.V.
 * @author <dd>Lic. Jaime Enrique Suárez Romero
 */
public class TGeneradorJS
    extends DAOBase
    implements Serializable {

/**
	 *
	 */
	private static final long serialVersionUID = -533365474056320813L;
private File archivo;
  private PrintWriter out;

  public boolean createJS(String cRuta, String cNomArchivo,
                          StringBuffer sbContenido, boolean lSobreescribir) {
    if (lSobreescribir) {
      this.borrarAnteriores(cRuta, cNomArchivo);
    }
    archivo = new File(cRuta, cNomArchivo);
    return this.record(sbContenido, archivo);
  }

private boolean record(StringBuffer sbContenido, File archivo) {
    boolean lSuccess = true;
    try {
      out = new PrintWriter(new FileWriter(archivo));
      out.println(sbContenido);
      out.close();
    }
    catch (IOException e) {
      warn("record", e);
      lSuccess = false;
    }
    finally {
      return lSuccess;
    }
  }

  private void borrarAnteriores(String cRuta, String cNomArchivo) {
    try {
      File dir = new File(cRuta.toString());
      String[] ls = dir.list();

      for (int k = 0; k < ls.length; k++) {
        File file = new File(cRuta, ls[k]);
        if (file.getName().equals(cNomArchivo)) {
          if (file.getAbsoluteFile().delete()) {
            ;//info("Eliminado: " + cNomArchivo);
          }
        }
      }
    }
    catch (Exception e) {
      warn("borrarAnteriores", e);
    }
  }

  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException,
      IOException {
    ois.defaultReadObject();
  }

}

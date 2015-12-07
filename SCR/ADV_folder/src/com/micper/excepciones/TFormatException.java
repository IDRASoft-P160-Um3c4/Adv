/*
 * formatException.java
 *
 * Created on 1 de diciembre de 2003, 05:04 PM
 */

package com.micper.excepciones;

import java.io.*;

/**
 *
 * @author  evalladares
 */
public class TFormatException extends Exception implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 8909409542487501249L;
	/** Creates a new instance of formatException */
    public TFormatException() {
    }
    public TFormatException(String msg) {
        super(msg);
    }
  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }

}

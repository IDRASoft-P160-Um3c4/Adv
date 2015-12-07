package com.micper.excepciones;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseException extends Exception {
  /**
	 *
	 */
	private static final long serialVersionUID = 4123322101850003734L;

public BaseException(String mensaje) {
    super(mensaje);
  }

}
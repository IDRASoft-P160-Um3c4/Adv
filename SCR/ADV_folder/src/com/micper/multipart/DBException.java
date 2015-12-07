/*
 * DBException.java
 *
 * Created on 24 de noviembre de 2003, 03:00 PM
 */

package com.micper.multipart;

/**
 *
 * @author  evalladares
 */
public class DBException extends Exception {

    /**
	 *
	 */
	private static final long serialVersionUID = 8578020784045389972L;

	/** Creates a new instance of DBException */
    public DBException() {
    }

    public DBException(String msg){
        super(msg);
    }


}

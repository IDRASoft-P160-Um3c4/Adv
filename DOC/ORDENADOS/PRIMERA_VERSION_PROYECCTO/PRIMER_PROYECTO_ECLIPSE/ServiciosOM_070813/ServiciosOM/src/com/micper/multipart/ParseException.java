/*
 * ParseException.java
 *
 * Created on 17 de noviembre de 2003, 12:52 PM
 */

package com.micper.multipart;

/**
 *
 * @author  evalladares
 */
public class ParseException extends Exception {

    /**
	 *
	 */
	private static final long serialVersionUID = 7104321275521940010L;
	/** Creates a new instance of ParseException */
    public ParseException() {
    }
    public ParseException(String msg){
        super(msg);
    }

}

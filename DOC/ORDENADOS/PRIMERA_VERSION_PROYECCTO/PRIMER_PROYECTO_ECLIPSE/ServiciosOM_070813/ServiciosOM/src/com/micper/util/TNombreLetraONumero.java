/*
 * TNombreLEtra.java
 *
 * Created on 18 de Abril de 2006
 */

package com.micper.util;
import java.util.*;
import java.io.*;
import com.micper.excepciones.*;

/**
 *
 * @author  ABarrientos
 */
public class TNombreLetraONumero implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = -8723987758281387454L;
	private HashMap letras;

 	public TNombreLetraONumero() {
        letras = new HashMap();
        letras.put("A", "A");
        letras.put("B", "BE");
        letras.put("C", "CE");
        letras.put("D", "DE");
        letras.put("E", "E");
        letras.put("F", "EFE");
        letras.put("G", "GE");
        letras.put("H", "ACHE");
        letras.put("I", "I");
        letras.put("J", "JOTA");
        letras.put("K", "KA");
        letras.put("L", "ELE");
        letras.put("M", "EME");
        letras.put("N", "ENE");
        letras.put("O", "O");
        letras.put("P", "PE");
        letras.put("Q", "QU");
        letras.put("R", "ERE");
        letras.put("S", "ESE");
        letras.put("T", "TE");
        letras.put("U", "U");
        letras.put("V", "UVE");
        letras.put("W", "DOBLEU");
        letras.put("X", "EQUIS");
        letras.put("Y", "EYE");
        letras.put("Z", "ZETA");
        letras.put("0", "CERO");
        letras.put("1", "UNO");
        letras.put("2", "DOS");
        letras.put("3", "TRES");
        letras.put("4", "CUATRO");
        letras.put("5", "CINCO");
        letras.put("6", "SEIS");
        letras.put("7", "SIETE");
        letras.put("8", "OCHO");
        letras.put("9", "NUEVE");
    }

    /**
     * Método para obtener el nombre de una letra
     * @param letra String
     * @throws TFormatException
     * @return String
     */
    public String getNombreLetraONumero(String letra)throws TFormatException{
       String nombreLetra = "";
       nombreLetra = letra.trim().toUpperCase();
       nombreLetra = (String)letras.get(letra);
       if(nombreLetra==null)
         nombreLetra = "No Identificada";
       return nombreLetra.trim();

    }


}

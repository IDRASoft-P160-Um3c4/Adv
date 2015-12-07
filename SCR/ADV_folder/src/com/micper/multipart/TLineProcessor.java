/*
 * TLineProcessor.java
 *
 * Created on 17 de noviembre de 2003, 02:59 PM
 */

package com.micper.multipart;
import java.util.*;
/**
 *
 * @author  evalladares
 */
public class TLineProcessor {

    /** Creates a new instance of TLineProcessor */
    public TLineProcessor() {
    }

	public static List getTokens(String linea, List rules)throws ParseException{
        Iterator rulesI = rules.iterator();
        List tokens = new ArrayList();
        while(rulesI.hasNext()){
            Rule r = (Rule) rulesI.next();
            String nombre = r.getNombre();
            String dato = null;
            int pos = r.getPos();
            int lon = r.getSize();
            try{
            dato = linea.substring(pos, pos+lon);
            }catch(IndexOutOfBoundsException ioobe){
            dato = linea.substring(pos);
            }

            if(r.validate(dato)){
                tokens.add(nombre+"|"+dato);
            }else{
                throw new ParseException("El dato: "+dato+" para el elemento " +nombre +" no coincide con el formato esperado" );
            }
        }
        return tokens;
    }
}

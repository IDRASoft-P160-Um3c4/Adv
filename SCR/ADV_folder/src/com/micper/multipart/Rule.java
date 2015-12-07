/*
 * Rule.java
 *
 * Created on 17 de noviembre de 2003, 03:04 PM
 */

package com.micper.multipart;
import java.util.regex.*;
/**
 *
 * @author  evalladares
 */
public class Rule {

    private String nombre;

    private int pos;

    private int size;

    private String regex;

    /** Creates a new instance of Rule */
    public Rule() {
    }

    public String toString(){
        return "Nombre: "+nombre+", Posicion: "+pos+", Longitud: "+size+", Regex: "+regex;
    }

    /** Getter for property nombre.
     * @return Value of property nombre.
     *
     */
    public java.lang.String getNombre() {
        return nombre;
    }

    /** Setter for property nombre.
     * @param nombre New value of property nombre.
     *
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }

    /** Getter for property pos.
     * @return Value of property pos.
     *
     */
    public int getPos() {
        return pos;
    }

    /** Setter for property pos.
     * @param pos New value of property pos.
     *
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /** Getter for property regex.
     * @return Value of property regex.
     *
     */
    public java.lang.String getRegex() {
        return regex;
    }

    /** Setter for property regex.
     * @param regex New value of property regex.
     *
     */
    public void setRegex(java.lang.String regex) {
        this.regex = regex;
    }

    /** Getter for property size.
     * @return Value of property size.
     *
     */
    public int getSize() {
        return size;
    }

    /** Setter for property size.
     * @param size New value of property size.
     *
     */
    public void setSize(int size) {
        this.size = size;
    }

    public boolean validate(String dato){
        Pattern p = Pattern.compile(this.getRegex());
        Matcher m = p.matcher(dato);
        return m.matches();
    }


}

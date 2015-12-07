/*
 * TAbono.java
 *
 * Created on 17 de noviembre de 2003, 01:32 PM
 */

package com.micper.multipart;

/**
 *
 * @author  evalladares
 */
public class Tabono {

    private String tipo;

    private String codigo;

    private String sucursal;

    private String refnum;

    private String refalnum;

    private String importe;

    private String subcodigo;

    /** Creates a new instance of TAbono */
    public Tabono() {
    }
    public String toString(){
        return "Tipo: "+tipo+", Codigo: "+codigo+", sucursal: "+sucursal+", ref numerica: "+refnum+", ref Alfanumerica: "+refalnum+", importe: "+importe+", subcodigo: "+subcodigo;
    }
    /** Getter for property codigo.
     * @return Value of property codigo.
     *
     */
    public java.lang.String getCodigo() {
        return codigo;
    }

    /** Setter for property codigo.
     * @param codigo New value of property codigo.
     *
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }

    /** Getter for property importe.
     * @return Value of property importe.
     *
     */
    public java.lang.String getImporte() {
        return importe;
    }

    /** Setter for property importe.
     * @param importe New value of property importe.
     *
     */
    public void setImporte(java.lang.String importe) {
        this.importe = importe;
    }

    /** Getter for property refalnum.
     * @return Value of property refalnum.
     *
     */
    public java.lang.String getRefalnum() {
        return refalnum;
    }

    /** Setter for property refalnum.
     * @param refalnum New value of property refalnum.
     *
     */
    public void setRefalnum(java.lang.String refalnum) {
        this.refalnum = refalnum;
    }

    /** Getter for property refnum.
     * @return Value of property refnum.
     *
     */
    public java.lang.String getRefnum() {
        return refnum;
    }

    /** Setter for property refnum.
     * @param refnum New value of property refnum.
     *
     */
    public void setRefnum(java.lang.String refnum) {
        this.refnum = refnum;
    }
    /** Getter for property subcodigo.
     * @return Value of property subcodigo.
     *
     */
    public java.lang.String getSubcodigo() {
        return subcodigo;
    }

    /** Setter for property subcodigo.
     * @param subcodigo New value of property subcodigo.
     *
     */
    public void setSubcodigo(java.lang.String subcodigo) {
        this.subcodigo = subcodigo;
    }

    /** Getter for property sucursal.
     * @return Value of property sucursal.
     *
     */
    public java.lang.String getSucursal() {
        return sucursal;
    }

    /** Setter for property sucursal.
     * @param sucursal New value of property sucursal.
     *
     */
    public void setSucursal(java.lang.String sucursal) {
        this.sucursal = sucursal;
    }

    /** Getter for property tipo.
     * @return Value of property tipo.
     *
     */
    public java.lang.String getTipo() {
        return tipo;
    }

    /** Setter for property tipo.
     * @param tipo New value of property tipo.
     *
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

}

package com.micper.seguridad.vo;

import java.io.*;

/**
 * <p>Title: Clase </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TVMenu implements Serializable{

/**
	 *
	 */
	private static final long serialVersionUID = -6451027607049511775L;
private int iCveSistema;
private int iOrden;
private String cDscMenu;
private String cReferencia;
private String cNomPagina;
private int iOpcPadre;
private int lBloqueado;
private int lActualizacion;
private int iNivel;

  private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
  }
  private void writeObject(ObjectOutputStream oos) throws IOException {
    oos.defaultWriteObject();
  }
  public String getCDscMenu() {
    return cDscMenu;
  }
  public String getCNomPagina() {
    return cNomPagina;
  }
  public String getCReferencia() {
    return cReferencia;
  }
  public int getICveSistema() {
    return iCveSistema;
  }
  public int getIOpcPadre() {
    return iOpcPadre;
  }
  public int getIOrden() {
    return iOrden;
  }
  public void setCDscMenu(String cDscMenu) {
    this.cDscMenu = cDscMenu;
  }
  public void setCNomPagina(String cNomPagina) {
    this.cNomPagina = cNomPagina;
  }
  public void setCReferencia(String cReferencia) {
    this.cReferencia = cReferencia;
  }
  public void setICveSistema(int iCveSistema) {
    this.iCveSistema = iCveSistema;
  }
  public void setIOpcPadre(int iOpcPadre) {
    this.iOpcPadre = iOpcPadre;
  }
  public void setIOrden(int iOrden) {
    this.iOrden = iOrden;
  }
  public void setLBloqueado(int lBloqueado) {
    this.lBloqueado = lBloqueado;
  }
  public int getLActualizacion() {
    return lActualizacion;
  }
  public void setLActualizacion(int lActualizacion) {
    this.lActualizacion = lActualizacion;
  }
  public int getLBloqueado() {
    return lBloqueado;
  }
  public int getINivel() {
    return iNivel;
  }
  public void setINivel(int iNivel) {
    this.iNivel = iNivel;
  }

}
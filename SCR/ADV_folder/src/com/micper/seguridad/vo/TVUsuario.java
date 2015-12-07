package com.micper.seguridad.vo;

import java.io.*;

public class TVUsuario implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1554037198345479795L;
	int iCveusuario;
	java.sql.Date dtRegistro;
	String cUsuario;
	String cPassword;
	String cNombre;
	String cApPaterno;
	String cApMaterno;
	String cCalle;
	String cColonia;
	int iCvePais;
	String cDscPais;
	int iCveEntidadFed;
	String cDscEntidadFed;
	int iCveMunicipio;
	String cDscMunicipio;
	int iCodigoPostal;
	String cTelefono;
	int iCveUnidadOrg;
	int lBloqueado;
	String cID;
	int iCveGrupo;

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}

	public String getID() {
		return cID;
	}

	public void setID(String cID) {
		this.cID = cID;
	}

	public String getCApMaterno() {
		return cApMaterno;
	}

	public String getCApPaterno() {
		return cApPaterno;
	}

	public String getCCalle() {
		return cCalle;
	}

	public String getCNombre() {
		return cNombre;
	}

	public String getCColonia() {
		return cColonia;
	}

	public String getCPassword() {
		return cPassword;
	}

	public String getCTelefono() {
		return cTelefono;
	}

	public String getCUsuario() {
		return cUsuario;
	}

	public java.sql.Date getDtRegistro() {
		return dtRegistro;
	}

	public int getICveEntidadFed() {
		return iCveEntidadFed;
	}

	public int getICveMunicipio() {
		return iCveMunicipio;
	}

	public int getICvePais() {
		return iCvePais;
	}

	public int getICveUnidadOrg() {
		return iCveUnidadOrg;
	}

	public int getICveusuario() {
		return iCveusuario;
	}

	public int getLBloqueado() {
		return lBloqueado;
	}

	public void setLBloqueado(int lBloqueado) {
		this.lBloqueado = lBloqueado;
	}

	public void setICveusuario(int iCveusuario) {
		this.iCveusuario = iCveusuario;
	}

	public void setICveUnidadOrg(int iCveUnidadOrg) {
		this.iCveUnidadOrg = iCveUnidadOrg;
	}

	public void setICvePais(int iCvePais) {
		this.iCvePais = iCvePais;
	}

	public void setICveMunicipio(int iCveMunicipio) {
		this.iCveMunicipio = iCveMunicipio;
	}

	public void setICveEntidadFed(int iCveEntidadFed) {
		this.iCveEntidadFed = iCveEntidadFed;
	}

	public void setICodigoPostal(int iCodigoPostal) {
		this.iCodigoPostal = iCodigoPostal;
	}

	public void setDtRegistro(java.sql.Date dtRegistro) {
		this.dtRegistro = dtRegistro;
	}

	public void setCUsuario(String cUsuario) {
		this.cUsuario = cUsuario;
	}

	public void setCTelefono(String cTelefono) {
		this.cTelefono = cTelefono;
	}

	public void setCPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	public void setCNombre(String cNombre) {
		this.cNombre = cNombre;
	}

	public void setCColonia(String cColonia) {
		this.cColonia = cColonia;
	}

	public void setCCalle(String cCalle) {
		this.cCalle = cCalle;
	}

	public void setCApPaterno(String cApPaterno) {
		this.cApPaterno = cApPaterno;
	}

	public void setCApMaterno(String cApMaterno) {
		this.cApMaterno = cApMaterno;
	}

	public String getCDscEntidadFed() {
		return cDscEntidadFed;
	}

	public void setCDscEntidadFed(String cDscEntidadFed) {
		this.cDscEntidadFed = cDscEntidadFed;
	}

	public String getCDscMunicipio() {
		return cDscMunicipio;
	}

	public void setCDscMunicipio(String cDscMunicipio) {
		this.cDscMunicipio = cDscMunicipio;
	}

	public String getCDscPais() {
		return cDscPais;
	}

	public void setCDscPais(String cDscPais) {
		this.cDscPais = cDscPais;
	}

	public void destroy() {
		//System.out.print("Destroy!!");
	}

	public int getiCveGrupo() {
		return iCveGrupo;
	}

	public void setiCveGrupo(int iCveGrupo) {
		this.iCveGrupo = iCveGrupo;
	}

}

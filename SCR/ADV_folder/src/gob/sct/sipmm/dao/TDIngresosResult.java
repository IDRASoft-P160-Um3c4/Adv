package gob.sct.sipmm.dao;

import java.util.Vector;

public class TDIngresosResult {
	private String cError;
	private Vector result;
	
	public TDIngresosResult(String cError, Vector result){
		this.cError = cError;
		this.result = result;
	}

	public String getCError(){
		return cError;
	}

	public Vector getResult(){
		return result;
	}	
}

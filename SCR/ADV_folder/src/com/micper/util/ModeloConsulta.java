package com.micper.util;

import com.micper.seguridad.vo.TVDinRep;

public class ModeloConsulta {

	private String ejercicio; 
	private String solicitud;
	private String fRegistro; 
	private String tipoPermiso;
	private String solicitante; 
	private String autopista; 
	private String kmSentido; 
	private String justificacion;
	private String diasTranscurridos; 
	private String estatus; 
	private String resolucionVT; 
	private String evalSTecnicos; 
	private String evalAJuridicos; 
	private String numPermiso;
	private String fImprPermiso; 
	private String oficinaOrigen;
	private String tienePNC; 
	private String fechaVT;

	public ModeloConsulta(){
		
	}
	
	public void setFechaVT(String fechaVT) {
		this.fechaVT = fechaVT;
	}

	public ModeloConsulta(TVDinRep datosSolicitud){
		
		datosSolicitud = this.calculaTextos(datosSolicitud);
		
		this.setEjercicio(datosSolicitud.getString("IEJERCICIO"));
		this.setSolicitud(datosSolicitud.getString("INUMSOLICITUD"));
		this.setfRegistro(datosSolicitud.getString("TSREGISTRO"));
		this.setTipoPermiso(datosSolicitud.getString("CDSCBREVETRAMITE"));
		this.setSolicitante(datosSolicitud.getString("CNOMBRECOMPLETO"));
		this.setAutopista(datosSolicitud.getString("CDSCBREVEOFICINA"));
		this.setKmSentido(datosSolicitud.getString("CKMSENTIDO"));
		this.setJustificacion(datosSolicitud.getString("CHECHOS"));
		this.setDiasTranscurridos(String.valueOf(Math.round(Float.parseFloat(datosSolicitud.getString("DIASTRANS")))));
		this.setEstatus(datosSolicitud.getString("LABANDONADA"));
		this.setResolucionVT(datosSolicitud.getString("RESOLVT"));
		this.setEvalAJuridicos(datosSolicitud.getString("LJURIDICO"));
		this.setEvalSTecnicos(datosSolicitud.getString("LTECNICO"));
		this.setNumPermiso(datosSolicitud.getString("COLF").equals("null")?"":datosSolicitud.getString("COLF"));
		this.setfImprPermiso(datosSolicitud.getString("COLG").equals("null")?"":datosSolicitud.getString("COLG"));
		this.setOficinaOrigen(datosSolicitud.getString("CDSCBREVEOFICINA"));
		this.setTienePNC(datosSolicitud.getString("ICONSECUTIVOPNC"));
		this.setFechaVT(datosSolicitud.getString("DTVISITA"));
	}

	public String getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	public String getfRegistro() {
		return fRegistro;
	}

	public void setfRegistro(String fRegistro) {
		this.fRegistro = fRegistro;
	}

	public String getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getAutopista() {
		return autopista;
	}

	public void setAutopista(String autopista) {
		this.autopista = autopista;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getDiasTranscurridos() {
		return diasTranscurridos;
	}

	public void setDiasTranscurridos(String diasTranscurridos) {
		this.diasTranscurridos = diasTranscurridos;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getResolucionVT() {
		return resolucionVT;
	}

	public void setResolucionVT(String resolucionVT) {
		this.resolucionVT = resolucionVT;
	}

	public String getEvalSTecnicos() {
		return evalSTecnicos;
	}

	public void setEvalSTecnicos(String evalSTecnicos) {
		this.evalSTecnicos = evalSTecnicos;
	}

	public String getEvalAJuridicos() {
		return evalAJuridicos;
	}

	public void setEvalAJuridicos(String evalAJuridicos) {
		this.evalAJuridicos = evalAJuridicos;
	}

	public String getNumPermiso() {
		return numPermiso;
	}

	public void setNumPermiso(String numPermiso) {
		this.numPermiso = numPermiso;
	}

	public String getfImprPermiso() {
		return fImprPermiso;
	}

	public void setfImprPermiso(String fImprPermiso) {
		this.fImprPermiso = fImprPermiso;
	}

	public String getOficinaOrigen() {
		return oficinaOrigen;
	}

	public void setOficinaOrigen(String oficinaOrigen) {
		this.oficinaOrigen = oficinaOrigen;
	}
	
	public String getTienePNC() {
		return tienePNC;
	}

	public void setTienePNC(String tienePNC) {
		this.tienePNC = tienePNC;
	}

	public String getFechaVT() {
		return fechaVT;
	}

	
	public String getKmSentido() {
		return kmSentido;
	}

	public void setKmSentido(String kmSentido) {
		this.kmSentido = kmSentido;
	}

	public TVDinRep calculaTextos(TVDinRep datosConsulta){

		if (!datosConsulta.getString("DTCANCELACION").equals("") && !datosConsulta.getString("DTCANCELACION").equals("null")){
			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","CANCELADO");
		}
		else if (!datosConsulta.getString("LABANDONADA").equals("") && Integer.parseInt(datosConsulta.getString("LABANDONADA"))> 0) {
			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","ABANDONADO");
		} 
		else if (Integer.parseInt(datosConsulta.getString("LRESOLUCIONPOSITIVA")) > 0 && !datosConsulta.getString("COLF").equals("null") &&  !datosConsulta.getString("COLG").equals("null")) {
			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","OTORGADO");
		} 
		else if (Integer.parseInt(datosConsulta.getString("LRESOLUCIONPOSITIVA"))==0 && !datosConsulta.getString("DTRESOLTRAM").equals("") && !datosConsulta.getString("DTRESOLTRAM").equals("null")){
			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","NEGADO");
		} 
		else if (Integer.parseInt(datosConsulta.getString("LRESOLUCIONPOSITIVA"))==0 && (datosConsulta.getString("DTRESOLTRAM").equals("")||datosConsulta.getString("DTRESOLTRAM").equals("null")) ){
			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","EN PROCESO");
		} 

		if (Integer.parseInt(datosConsulta.getString("LTECNICO"))>0){
			datosConsulta.remove("LTECNICO");
			datosConsulta.put("LTECNICO","EVALUADO");
		}
		else{
			datosConsulta.remove("LTECNICO");
			datosConsulta.put("LTECNICO","PENDIENTE");
		}
        
		if (Integer.parseInt(datosConsulta.getString("LJURIDICO"))>0){
			datosConsulta.remove("LJURIDICO");
			datosConsulta.put("LJURIDICO","EVALUADO");
		}
		else{
			datosConsulta.remove("LJURIDICO");
			datosConsulta.put("LJURIDICO","PENDIENTE");
		}

		if (datosConsulta.getString("RESOLVT").equals("NEGATIVO")) {

			datosConsulta.remove("LABANDONADA");
			datosConsulta.put("LABANDONADA","NEGADA");
			
			datosConsulta.remove("LTECNICO");
			datosConsulta.put("LTECNICO","NO APLICA");
			
			datosConsulta.remove("LJURIDICO");
			datosConsulta.put("LJURIDICO","NO APLICA");
						
			datosConsulta.remove("CNUMPERMISO");
			datosConsulta.put("CNUMPERMISO","NO APLICA");
			
			datosConsulta.remove("DTPERMISO");
			datosConsulta.put("DTPERMISO","NO APLICA");

		}
		
		if (datosConsulta.getString("DTPERMISO").equals("null")) {
			datosConsulta.remove("DTPERMISO");
			datosConsulta.put("DTPERMISO","");
		}
		
		if (datosConsulta.getInt("ICONSECUTIVOPNC") > 0) {
			datosConsulta.remove("ICONSECUTIVOPNC");
			datosConsulta.put("ICONSECUTIVOPNC","SI");
		}else{
			datosConsulta.remove("ICONSECUTIVOPNC");
			datosConsulta.put("ICONSECUTIVOPNC","NO");		
		}
		
		if (datosConsulta.getString("DTVISITA").equals("null") || datosConsulta.getString("DTVISITA").equals("")) {
			datosConsulta.remove("DTVISITA");
			datosConsulta.put("DTVISITA","");
		}
		
		if (datosConsulta.getString("LABANDONADA").equals("OTORGADO")){
			datosConsulta.remove("DIASTRANS");
			datosConsulta.put("DIASTRANS",datosConsulta.getString("DIASPERM"));
		}
		
		if (datosConsulta.getString("LABANDONADA").equals("NEGADO")){
			if(Float.valueOf(datosConsulta.getString("DIASRESOL"))>0){
				datosConsulta.remove("DIASTRANS");
				datosConsulta.put("DIASTRANS",datosConsulta.getString("DIASRESOL"));
			}
		}

		if (datosConsulta.getString("LABANDONADA").equals("CANCELADO")){
			datosConsulta.remove("DIASTRANS");
			datosConsulta.put("DIASTRANS",datosConsulta.getString("DIASCANCELA"));
		}
		
		return datosConsulta;
	}
}

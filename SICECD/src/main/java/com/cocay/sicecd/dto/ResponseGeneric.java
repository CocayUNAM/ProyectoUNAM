package com.cocay.sicecd.dto;

public class ResponseGeneric {
	
	private String cdMensaje;
	private String nbMensaje;
	private Object response;
	
	public String getCdMensaje() {
		return cdMensaje;
	}
	public void setCdMensaje(String cdMensaje) {
		this.cdMensaje = cdMensaje;
	}
	public String getNbMensaje() {
		return nbMensaje;
	}
	public void setNbMensaje(String nbMensaje) {
		this.nbMensaje = nbMensaje;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}

}

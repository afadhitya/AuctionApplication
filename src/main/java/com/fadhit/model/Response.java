package com.fadhit.model;

public class Response {
	
	private boolean status;
	private String message;
	
	
	
	public Response(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public Response(boolean status) {
		super();
		this.status = status;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

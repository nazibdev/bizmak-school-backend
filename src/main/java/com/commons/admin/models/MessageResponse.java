package com.commons.admin.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;
	private Object response;
	private Map<String, String> list = new HashMap<>();
	private Date date = new Date();
	private String dateStamp;
	
	public MessageResponse(String string) {
		this.message = string;
	}
	
	public MessageResponse(boolean success, String string) {
		this.success = success;
		this.message = string;
	}
	
	public MessageResponse(boolean success, String string, Object... response) {
		this.success = success;
		this.message = string;
		this.response = response;
	}
	
	public MessageResponse(boolean success, String string, Map<String, String> list) {
		
		this.dateStamp = date.toString();
		this.success = success;
		this.message = string;
		this.list = list;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setObject(Object response) {
		this.response = response;
	}
	
	public Object getObjects() {
		return response;
	}
	
	
	public void setList(Map<String, String> list) {
		this.list = list;
	}
	
	public Map<String, String> getList() {
		return list;
	}

}

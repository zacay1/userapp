package com.user.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
	private String message;

	public GenericResponse() {
		// TODO Auto-generated constructor stub
	}

	public GenericResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

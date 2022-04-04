package com.user.pojo.response;

public class TokenResponse extends GenericResponse {

	private String token;

	public TokenResponse(String message, String token) {
		super(message);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

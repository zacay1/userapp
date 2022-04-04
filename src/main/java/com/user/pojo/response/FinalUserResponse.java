package com.user.pojo.response;

public class FinalUserResponse extends GenericResponse {
	private UserResponse user;

	public FinalUserResponse() {
		// TODO Auto-generated constructor stub
	}

	public FinalUserResponse(String message, UserResponse user) {
		super(message);
		this.setUser(user);
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}
}

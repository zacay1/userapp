package com.user.v1.body;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

public class PasswordBody {
	@Schema(description = "User's password", required = true, example = "hunter2*")
	@NotBlank(message = "password is mandatory")
	private String password;

	public PasswordBody() {
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

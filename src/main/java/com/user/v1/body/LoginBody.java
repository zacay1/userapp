package com.user.v1.body;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginBody {
	@Schema(description = "User's e-mail address", required = true, example = "juan@rodriguez.org")
	@NotBlank(message = "email is mandatory")
	@Pattern(regexp = "[_A-Za-z0-9-]+((\\.|\\+)?[_A-Za-z0-9-]+)*@[A-Za-z0-9]+((\\.|-)?[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$", message = "Invalid email format")
	private String email;
	@Schema(description = "User's password", required = true, example = "hunter2*")
	@NotBlank(message = "password is mandatory")
	private String password;

	public LoginBody(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

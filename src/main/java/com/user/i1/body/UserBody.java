package com.user.i1.body;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user.persistence.entity.AppUser;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserBody {
	@Schema(description = "User's name", required = true, example = "Juan Rodriguez")
	@NotBlank(message = "name is mandatory")
	private String name;
	@Schema(description = "User's e-mail address", required = true, example = "juan@rodriguez.org")
	@NotBlank(message = "email is mandatory")
	@Pattern(regexp = "[_A-Za-z0-9-]+((\\.|\\+)?[_A-Za-z0-9-]+)*@[A-Za-z0-9]+((\\.|-)?[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$", message = "Invalid email format")
	private String email;
	@Schema(description = "User's password", required = true, example = "hunter2*")
	@NotBlank(message = "password is mandatory")
	private String password;
	@Schema(description = "Contact information", required = true)
	private List<PhoneBody> phones;

	public UserBody() {
		// TODO Auto-generated constructor stub
	}

	public UserBody(AppUser appUser) {
		super();
		this.name = appUser.getName();
		this.email = appUser.getEmail();
		this.phones = new ArrayList<PhoneBody>();
		appUser.getPhones().forEach(phone -> {
			this.phones.add(new PhoneBody(phone));
		});
	}

	public UserBody(String name, String email, String password, List<PhoneBody> phones) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<PhoneBody> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneBody> phones) {
		this.phones = phones;
	}

}

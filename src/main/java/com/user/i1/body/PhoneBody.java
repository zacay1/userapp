package com.user.i1.body;

import javax.validation.constraints.NotBlank;

import com.user.persistence.entity.AppPhone;

import io.swagger.v3.oas.annotations.media.Schema;

public class PhoneBody {
	@Schema(description = "Number of phone", required = true, example = "1234567")
	@NotBlank(message = "number is mandatory")
	private String number;
	@Schema(description = "City code", required = true, example = "1")
	@NotBlank(message = "citycode is mandatory")
	private String citycode;
	@Schema(description = "Contry code", required = true, example = "57")
	@NotBlank(message = "contrycode is mandatory")
	private String contrycode;

	public PhoneBody() {
		// TODO Auto-generated constructor stub
	}

	public PhoneBody(AppPhone appPhone) {
		super();
		this.number = appPhone.getNumber();
		this.citycode = appPhone.getNumber();
		this.contrycode = appPhone.getContrycode();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

}

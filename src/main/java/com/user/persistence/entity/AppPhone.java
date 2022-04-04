package com.user.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.user.i1.body.PhoneBody;

@Entity(name = "app_phone")
@Table(name = "app_phone")
public class AppPhone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String number;
	private String citycode;
	private String contrycode;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private AppUser appUserReference;

	public AppPhone() {
		// TODO Auto-generated constructor stub
	}

	public AppPhone(PhoneBody phone) {
		super();
		this.number = phone.getNumber();
		this.citycode = phone.getCitycode();
		this.contrycode = phone.getContrycode();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppUser getAppUserReference() {
		return appUserReference;
	}

	public void setAppUserReference(AppUser appUserReference) {
		this.appUserReference = appUserReference;
	}

}

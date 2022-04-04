package com.user.persistence.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.user.i1.body.UserBody;

@Entity(name = "app_user")
@Table(name = "app_user", uniqueConstraints = {
		@UniqueConstraint(name = "USER_EMAIL_UNIQUE_CONSTRAINTS", columnNames = { "email" }) })
public class AppUser {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	private String name;
	private String email;
	private String password;
	private Instant created;
	private Instant modified;
	private Instant lastLogin;
	private Boolean active;
	private String token;

	@OneToMany(mappedBy = "appUserReference", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<AppPhone> phones;

	public AppUser() {
		// TODO Auto-generated constructor stub
	}

	public AppUser(UserBody user) {
		super();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.setModified(Instant.now());
		this.active = true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public Instant getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Instant lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getModified() {
		return modified;
	}

	public void setModified(Instant modified) {
		this.modified = modified;
	}

	public List<AppPhone> getPhones() {
		return phones;
	}

	public void setPhones(List<AppPhone> phones) {
		this.phones = phones;
	}

}

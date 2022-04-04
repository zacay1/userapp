package com.user.pojo.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user.i1.body.UserBody;
import com.user.persistence.entity.AppUser;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends UserBody {
	@Schema(description = "User id", required = true, example = "c9171b70-4df8-4abd-86ff-dc17ee7a751d")
	private String id;
	@Schema(description = "User creation date. UTC format", required = true, example = "2022-04-03T05:46:52.864Z")
	private Instant created;
	@Schema(description = "User modified date. UTC format", required = true, example = "2022-04-03T05:46:52.864Z")
	private Instant modified;
	@Schema(description = "date of last login. UTC format", required = true, example = "2022-04-03T05:46:52.864Z")
	private Instant lastLogin;
	@Schema(description = "Notifies if the user is active", required = true, example = "true")
	private Boolean isactive;
	@Schema(description = "API access JWT token", required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW51ZWxAdGVzdC5jbyIsImNyZWF0aW9uRGF0ZSI6MTY0ODk1NDQyMzAwNCwiZXhwIjoxNjQ4OTU0NDMzLCJpYXQiOjE2NDg5NTQ0MjN9.l7ZJhSup669t07YwMzhaK3QvHzyg15BU_isI_oTPFh-4gKEnzq0NLcFa5VBiaUfjCX2ltwzEBPB9vw-teEcQog")
	private String token;

	public UserResponse() {
		// TODO Auto-generated constructor stub
	}

	public UserResponse(AppUser appUser) {
		super(appUser);
		this.id = appUser.getId().toString();
		this.created = appUser.getCreated();
		this.setModified(appUser.getModified());
		this.lastLogin = appUser.getLastLogin();
		this.setIsactive(appUser.isActive());
		this.token = appUser.getToken();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public Instant getModified() {
		return modified;
	}

	public void setModified(Instant modified) {
		this.modified = modified;
	}

}

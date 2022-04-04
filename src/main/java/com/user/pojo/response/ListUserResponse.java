package com.user.pojo.response;

import java.util.List;

public class ListUserResponse extends GenericResponse {
	private List<UserResponse> items;

	public ListUserResponse(String message, List<UserResponse> items) {
		super(message);
		this.setItems(items);
	}

	public List<UserResponse> getItems() {
		return items;
	}

	public void setItems(List<UserResponse> items) {
		this.items = items;
	}
}

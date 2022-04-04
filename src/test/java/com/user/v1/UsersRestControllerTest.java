package com.user.v1;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.i1.Users;
import com.user.i1.body.UserBody;
import com.user.persistence.entity.AppUser;
import com.user.pojo.response.FinalUserResponse;
import com.user.pojo.response.GenericResponse;
import com.user.pojo.response.ListUserResponse;
import com.user.pojo.response.UserResponse;
import com.user.service.UserService;
import com.user.utils.Messages;

@WebMvcTest(Users.class)
public class UsersRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private String uId = "1a000000-b000-c000-d000-e00000000000";

	private String basicPath = "/i1/users";

	@BeforeEach
	public void setup() throws Exception {
		AppUser newAppUser = TestUtils.createUser("new", uId);
		Mockito.when(userService.create(Mockito.any(UserBody.class)))
				.thenReturn(new ResponseEntity<>(
						new FinalUserResponse(Messages.DESCRIPTION_USER_CREATE, new UserResponse(newAppUser)),
						HttpStatus.CREATED));

		AppUser modAppUser = TestUtils.createUser("mod", uId);
		Mockito.when(userService.update(Mockito.any(UserBody.class), Mockito.anyString()))
				.thenReturn(new ResponseEntity<>(
						new FinalUserResponse(Messages.TEXT_SUCCESSFUL_OPERATION, new UserResponse(modAppUser)),
						HttpStatus.OK));

		Mockito.when(userService.view(uId))
				.thenReturn(new ResponseEntity<>(
						new FinalUserResponse(Messages.TEXT_SUCCESSFUL_OPERATION, new UserResponse(newAppUser)),
						HttpStatus.OK));
		List<UserResponse> items = new ArrayList<UserResponse>();
		items.add(new UserResponse(TestUtils.createUser("new1", "1a000000-b000-c000-d000-e00000000000")));
		items.add(new UserResponse(TestUtils.createUser("new2", "2a000000-b000-c000-d000-e00000000000")));
		items.add(new UserResponse(TestUtils.createUser("new3", "3a000000-b000-c000-d000-e00000000000")));
		Mockito.when(userService.list()).thenReturn(
				new ResponseEntity<>(new ListUserResponse(Messages.TEXT_SUCCESSFUL_OPERATION, items), HttpStatus.OK));

		Mockito.when(userService.delete(uId)).thenReturn(
				new ResponseEntity<>(new GenericResponse(Messages.TEXT_SUCCESSFUL_OPERATION), HttpStatus.OK));

		Mockito.when(userService.active(uId, true)).thenReturn(
				new ResponseEntity<>(new GenericResponse(Messages.TEXT_SUCCESSFUL_OPERATION), HttpStatus.OK));

	}

	private ObjectMapper omMapper = new ObjectMapper();

	@Test
	public void create() throws Exception {
		AppUser newAppUser = TestUtils.createUser("new");
		UserBody uBody = new UserBody(newAppUser);
		uBody.setPassword(newAppUser.getPassword());
		mockMvc.perform(post(basicPath).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(omMapper.writeValueAsString(uBody))).andExpect(status().is(HttpStatus.CREATED.value()))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.DESCRIPTION_USER_CREATE)))
				.andExpect(jsonPath("$.user.id").exists()).andExpect(jsonPath("$.user.id", is(uId)))
				.andExpect(jsonPath("$.user.email").exists())
				.andExpect(jsonPath("$.user.email", is("newjuan@rodriguez.org")));
	}

	@Test
	public void update() throws Exception {
		AppUser newAppUser = TestUtils.createUser("mod");
		UserBody uBody = new UserBody(newAppUser);
		uBody.setPassword(newAppUser.getPassword());
		mockMvc.perform(put(String.format("%s/{id}", basicPath), uId).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(omMapper.writeValueAsString(uBody)))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(jsonPath("$.user.id").exists())
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.TEXT_SUCCESSFUL_OPERATION)))
				.andExpect(jsonPath("$.user.id", is(uId))).andExpect(jsonPath("$.user.email").exists())
				.andExpect(jsonPath("$.user.email", is("modjuan@rodriguez.org")));
	}

	@Test
	public void viewUser() throws Exception {
		mockMvc.perform(get(String.format("%s/{id}", basicPath), uId)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.TEXT_SUCCESSFUL_OPERATION)))
				.andExpect(jsonPath("$.user.id").exists()).andExpect(jsonPath("$.user.id", is(uId)));
	}

	@Test
	public void listUsers() throws Exception {
		mockMvc.perform(get(basicPath)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.TEXT_SUCCESSFUL_OPERATION)))
				.andExpect(jsonPath("$.items").exists()).andExpect(jsonPath("$.items").isArray())
				.andExpect(jsonPath("$.items.*", hasSize(3)));
	}

	@Test
	public void deleteUser() throws Exception {
		mockMvc.perform(delete(String.format("%s/{id}", basicPath), uId)).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.TEXT_SUCCESSFUL_OPERATION)));
	}

	@Test
	public void activeUser() throws Exception {
		mockMvc.perform(patch(String.format("%s/{id}/active?enable=true", basicPath), uId))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message", is(Messages.TEXT_SUCCESSFUL_OPERATION)));
	}

}

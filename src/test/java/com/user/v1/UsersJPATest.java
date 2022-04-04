package com.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user.persistence.entity.AppUser;
import com.user.persistence.repository.AppUserRepository;

@DataJpaTest
public class UsersJPATest {

	AppUserRepository userRepository;

	@Test
	public void createUser() {
		AppUser appUser = TestUtils.createUser("1");
		userRepository.save(appUser);
		assertNotNull("The identifier cannot be null", appUser.getId());
	}

	@Test
	public void updateUser() {
		AppUser appUser = TestUtils.createUser("1");
		userRepository.save(appUser);
		appUser.setEmail("md_juan@rodriguez.org");
		AppUser appUserMd = userRepository.save(appUser);
		assertNotNull("The identifier cannot be null", appUser.getId());
		assertNotNull("The identifier cannot be null", appUserMd.getId());
		assertEquals(appUser.getId(), appUserMd.getId());
		assertEquals(appUserMd.getEmail(), "md_juan@rodriguez.org");
	}

	@Test
	public void viewUser() throws JsonProcessingException {
		AppUser appUser = TestUtils.createUser("1");
		userRepository.save(appUser);
		assertNotNull("The identifier cannot be null", appUser.getId());
		Optional<AppUser> oAppUser = userRepository.findById(appUser.getId());
		assertNotNull("The Optional cannot be null", oAppUser);
		assertEquals(appUser, oAppUser.get());
	}

	@Test
	public void listUsers() throws JsonProcessingException {

		List<AppUser> listAppUsers = new ArrayList<AppUser>();
		listAppUsers.add(TestUtils.createUser("1"));
		listAppUsers.add(TestUtils.createUser("2"));
		listAppUsers.add(TestUtils.createUser("3"));
		userRepository.saveAll(listAppUsers);
		assertThat(userRepository.findAll()).hasSize(3);
	}

	@Test
	public void deleteUser() throws JsonProcessingException {
		AppUser appUser = TestUtils.createUser("1");
		userRepository.save(appUser);
		assertNotNull("The identifier cannot be null", appUser.getId());
		userRepository.deleteById(appUser.getId());
		assertTrue(userRepository.findById(appUser.getId()).isEmpty());
	}

}

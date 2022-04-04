package com.user.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.webjars.NotFoundException;

import com.user.i1.body.UserBody;
import com.user.persistence.entity.AppPhone;
import com.user.persistence.entity.AppUser;
import com.user.persistence.repository.AppPhoneRepository;
import com.user.persistence.repository.AppUserRepository;
import com.user.pojo.response.FinalUserResponse;
import com.user.pojo.response.GenericResponse;
import com.user.pojo.response.ListUserResponse;
import com.user.pojo.response.UserResponse;
import com.user.utils.JwtUtil;
import com.user.utils.Messages;

@Service
public class UserService {

	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private AppPhoneRepository phoneRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public ResponseEntity<GenericResponse> create(@RequestBody UserBody user) throws Exception {
		return upsert(user, null, Messages.DESCRIPTION_USER_CREATE);
	}

	public ResponseEntity<GenericResponse> update(@RequestBody UserBody user, String id) throws Exception {
		AppUser appUser = getUser(id);
		return upsert(user, appUser.getId(), Messages.TEXT_SUCCESSFUL_OPERATION);
	}

	public ResponseEntity<GenericResponse> view(String id) throws Exception {

		return new ResponseEntity<>(
				new FinalUserResponse(Messages.TEXT_SUCCESSFUL_OPERATION, new UserResponse(getUser(id))),
				HttpStatus.OK);
	}

	public ResponseEntity<GenericResponse> list() throws Exception {
		List<UserResponse> items = new ArrayList<UserResponse>();
		userRepository.findAll().forEach(appUser -> {
			items.add(new UserResponse(appUser));
		});

		return new ResponseEntity<>(new ListUserResponse(Messages.TEXT_SUCCESSFUL_OPERATION, items), HttpStatus.OK);
	}

	public ResponseEntity<GenericResponse> delete(String id) throws Exception {
		userRepository.delete(getUser(id));
		return new ResponseEntity<>(new GenericResponse(Messages.TEXT_SUCCESSFUL_OPERATION), HttpStatus.OK);
	}

	public ResponseEntity<GenericResponse> active(String id, boolean enable) throws Exception {
		AppUser appUser = getUser(id);
		appUser.setActive(enable);
		userRepository.save(appUser);
		return new ResponseEntity<>(new GenericResponse(Messages.TEXT_SUCCESSFUL_OPERATION), HttpStatus.OK);
	}

	private AppUser getUser(String id) throws Exception {
		UUID userId;
		try {
			userId = UUID.fromString(id);
		} catch (Exception e) {
			throw new ValidationException(Messages.ERROR_USER_INVALID_ID);
		}
		Optional<AppUser> oAppUser = userRepository.findById(userId);
		if (oAppUser.isEmpty())
			throw new NotFoundException(Messages.ERROR_USER_NOT_FOUND);

		return oAppUser.get();
	}

	private ResponseEntity<GenericResponse> upsert(@RequestBody UserBody user, UUID userId, String message)
			throws Exception {
		String passwordRegex = environment.getProperty("user.password.regex");
		if (passwordRegex != null && !passwordRegex.isEmpty()) {
			Pattern pattern = Pattern.compile(passwordRegex);
			if (!pattern.matcher(user.getEmail()).find()) {
				throw new ValidationException(Messages.ERROR_USER_PASSWORD_FORMAT);
			}
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		AppUser appUser = new AppUser(user);
		appUser.setId(userId);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("creationDate", Instant.now().toEpochMilli());
		appUser.setToken(JwtUtil.generateToken(claims, appUser.getEmail(), environment));
		HttpStatus status = HttpStatus.OK;
		if (userId == null) {
			appUser.setCreated(Instant.now());
			appUser.setLastLogin(Instant.now());
			status = HttpStatus.CREATED;
		} else {
			phoneRepository.deleteAllByAppUserReference(appUser);
		}

		List<AppPhone> phones = new ArrayList<AppPhone>();
		user.getPhones().forEach(phone -> {
			AppPhone newPhone = new AppPhone(phone);
			newPhone.setAppUserReference(appUser);
			phones.add(newPhone);
		});
		appUser.setPhones(phones);
		userRepository.save(appUser);
		phoneRepository.saveAll(phones);

		return new ResponseEntity<>(new FinalUserResponse(message, new UserResponse(appUser)), status);
	}

}

package com.user.service;

import java.time.Instant;
import java.util.regex.Pattern;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.exceptions.ForbiddenException;
import com.user.exceptions.UnauthorizedException;
import com.user.persistence.entity.AppUser;
import com.user.persistence.repository.AppUserRepository;
import com.user.pojo.response.GenericResponse;
import com.user.pojo.response.TokenResponse;
import com.user.utils.JwtUtil;
import com.user.utils.Messages;
import com.user.v1.body.LoginBody;

@Service
public class MyselfService {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private Environment environment;

	public ResponseEntity<GenericResponse> Login(LoginBody login) {
		AppUser user = userRepository.findByEmail(login.getEmail());
		if (user == null || !user.isActive())
			throw new ForbiddenException(Messages.ERROR_USER_FORBIDDEN);

		if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
			throw new UnauthorizedException(Messages.ERROR_USER_UNAUTHORIZED);
		user.setLastLogin(Instant.now());
		userRepository.save(user);
		return new ResponseEntity<>(new TokenResponse(Messages.TEXT_SUCCESSFUL_OPERATION, user.getToken()),
				HttpStatus.OK);

	}

	public ResponseEntity<GenericResponse> updatePasword(String token, String password) {
		AppUser user = userRepository.findByEmail(JwtUtil.getUser(token, environment));
		if (user == null)
			throw new ForbiddenException(Messages.ERROR_USER_FORBIDDEN);
		String passwordRegex = environment.getProperty("user.password.regex");
		if (passwordRegex != null && !passwordRegex.isEmpty()) {
			Pattern pattern = Pattern.compile(passwordRegex);
			if (!pattern.matcher(user.getEmail()).find()) {
				throw new ValidationException(Messages.ERROR_USER_PASSWORD_FORMAT);
			}
		}
		user.setPassword(passwordEncoder.encode(password));
		user.setModified(Instant.now());
		userRepository.save(user);
		return new ResponseEntity<>(new GenericResponse(Messages.TEXT_SUCCESSFUL_OPERATION), HttpStatus.OK);

	}

}

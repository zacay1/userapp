package com.user.v1;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.exceptions.ProcessExctions;
import com.user.pojo.response.GenericResponse;
import com.user.pojo.response.TokenResponse;
import com.user.service.MyselfService;
import com.user.utils.Constants;
import com.user.utils.Messages;
import com.user.v1.body.LoginBody;
import com.user.v1.body.PasswordBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/v1/myself")
@SecurityRequirement(name = "Jwt_Token")
public class Myself {

	@Autowired
	private MyselfService myselfService;

	@Operation(summary = Messages.TITLE_MYSELF_LOGIN)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@PostMapping("/login")
	private ResponseEntity<GenericResponse> login(@Valid @RequestBody LoginBody userLogin) {
		try {
			return myselfService.Login(userLogin);
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}

	}

	@Operation(summary = Messages.TITLE_MYSELF_PASSWORD)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "403", description = "Forbidden", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@PatchMapping("/password")
	private ResponseEntity<GenericResponse> updatePassword(@Valid @RequestBody PasswordBody password,
			HttpServletRequest request) {
		try {
			return myselfService.updatePasword(request.getHeader(Constants.HEADER_AUTHORIZATION),
					password.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}

	}
}
